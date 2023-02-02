package sourtify;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Sourtify {

//    static String save = "C:"+File.separator+"sourtify" + File.separator + "result"; // 저장 위치

    public static void findProtoMethod(String filename, String keyword, List<String> functionList) throws IOException {
        String line;
        boolean start = false; // 메서드 생성 시작 여부

        BufferedReader br = new BufferedReader(
                new FileReader(filename)
        );

        while((line = br.readLine()) != null) {
            String tline = line.trim().replaceAll(" ", "");
            String chkFuncKeyword = "=function";
            String decProtoKeyword = keyword + ".prototype."; // prototype method 선언 추가를 위해서

            int endIndex = tline.indexOf(chkFuncKeyword);
            // stack size와 상관없다(이건 객체 선언 밖에서 이뤄지기 때문에.. 의미 없다)
            // Wrapper.prototype.anyfunc=function(arg) { ... }
            if (tline.indexOf(decProtoKeyword) > -1 && endIndex > -1) {
                String funcName = tline.substring(decProtoKeyword.length(), endIndex);
                functionList.add(funcName);
                // prototype 선언이기때문에 그냥 넘어간다
            }
        }
    }

    public static void findObjectMethod(String filename, String keyword, List<String> functionList) throws IOException {
        findObjectMethod(filename, keyword, functionList, null);
    }

    public static void findObjectMethod(String filename, String keyword, List<String> functionList, PrintWriter writer) throws IOException {
        String line;
        boolean start = false; // 메서드 생성 시작 여부

        Stack<String> stack = new Stack<>();

        BufferedReader br = new BufferedReader(
                new FileReader(filename)
        );

        while((line = br.readLine()) != null) {
            String tline = line.trim().replaceAll(" ", "");
            String chkFuncKeyword = "=function";
            String chkFuncKeyword2 = "function";
//            String endKey = keyword + "."; // 객체 전역 선언이 시작됨을 알림(메서드 선언이 아님)
            String decMethodKeyword = "this.";

//            System.out.println(tline);
//            if (writer != null) {
//                writer.write(tline+"\n");
//            }

            // 처음 객체 찾기
            // ex1) Wrapper = function(id) { ... }
            // ex2) function Wrapper(id) { ... }
            if (tline.indexOf(keyword + chkFuncKeyword) > -1 || tline.indexOf(chkFuncKeyword2 + keyword) > -1) {
                start = true;
            }

            if (start == true) {
                boolean chkBreak = false;
                String[] arrLine = tline.split("");
                for (int i = 0; i < tline.length(); ++i) {
                    if (arrLine[i].equals("{")) {
                        stack.push("{");
//                        System.out.println("push { : stack size : " + stack.size());
                    } else if (arrLine[i].equals("}")) {
                        stack.pop();
//                        System.out.println("pop {} : stack size : " + stack.size());
                        if (stack.size() == 0) {
                            // 메서드 선언부 종료
                            System.out.println("<> Check Method End Point <>");
                            start = false;
                            chkBreak = true;
                            break;
                        }
                    }
                }
                
                // 탐색 종료
                if (chkBreak) {
                    break;
                }

                /*
                if (tline.indexOf('{') > -1) {
                    stack.push("{");
//                    System.out.println(">>> Stack push { ");
                    // 세트로 처리
                    if (tline.indexOf('}') > -1) {
                        stack.pop();
//                        System.out.println(">>> Stack pop {} ");
                        if (stack.size() == 0) {
                            // 메서드 선언부 종료
//                                System.out.println("여기다!1");
                            break;
                        }
                    }
//                    continue;
                } else if (tline.indexOf('}') > -1) {
                    stack.pop();
//                    System.out.println(">>> Stack pop {} ");
                    if (stack.size() == 0) {
                        // 메서드 선언부 종료
//                        System.out.println("여기다!2");
                        break;
                    }
                }
                */
            }

            // 계속 객체 메서드 생성 중이면
            if (start == true && stack.size() > 0) {
//                System.out.println(">>> 메서드 생성중 - stack size : " + stack.size());
                int endIndex = tline.indexOf(chkFuncKeyword);
//                System.out.println("Yest : " + tline.indexOf(decMethodKeyword) + " / " + endIndex);
                // 메서드는 this. 으로 객체 내부 선언되기 때문에 "this.***=function" 규칙으로 찾는다.
                if (tline.indexOf(decMethodKeyword) > -1 && endIndex > -1) {
//                    System.out.println(tline);
                    String funcName = tline.substring(decMethodKeyword.length(), endIndex);
//                    System.out.println(">>> funcName : " + funcName);

//                    if (writer != null) {
//                        writer.write("funcName: " + funcName + "\n");
//                    }
                    functionList.add(funcName);
                }
            }
        }

        br.close();

//        if (writer != null) {
//        writer.close();
//        }
    }

    public static void main(String[] args) throws Exception {
        String keyword = "";
        String filePath = "";
//        String keyword = "IBSheet7Wrapper";
//        String keyword2 = "dhtmlXGridObject";
//        String line;
        boolean start = false; // 메서드 생성 시작 여부

        // js file path
        System.out.println(">>> 1. Input JS File Path");
        BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(System.in));
        filePath = bufferedReader1.readLine();
        System.out.println("<<< Input JS File Path : " + filePath);

        // keyword
        System.out.println(">>> 2. Input Keyword");
        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(System.in));
        keyword = bufferedReader2.readLine();
        System.out.println("<<< Input Keyword : " + keyword);

        // 저장할 파일
//        PrintWriter writer = new PrintWriter(new FileWriter(save + "_" + keyword +".txt"));

        Queue<String> queue = new LinkedList<>();
        Stack<String> stack = new Stack<>();
        List<String> functionList = new LinkedList<>();

        if (keyword.isEmpty() || keyword.equals("")) {
            System.out.println("Keyword is Empty, End Process.");
            System.exit(0);
        }

        System.out.println(">>> SEARCH START >>>\n");

        String filename = filePath;
//        String filename = "C:\\sourtify\\ibsheet7.js";
//        String filename = "C:\\sourtify\\grid.js";

        // 실행 1. 객체 선언 메서드
//        findObjectMethod(filename, keyword, functionList, writer);
        findObjectMethod(filename, keyword, functionList);
        // 실행 2. prototype 선언 메서드
        findProtoMethod(filename, keyword, functionList);

        // funcList 반복 출력
        if (functionList.size() > 0) {
            for (String funcName : functionList) {
                System.out.println("funcName : " + funcName);
            }

            System.out.println("\nfuncCount : " + functionList.size());
        } else {
            System.out.println("Function List is Empty");
        }

        System.out.println("\n>>> END >>>");
    }
}
