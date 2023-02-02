package fc;

import java.io.*;
import java.util.*;

public class FindFunc {

    static String SAVE_PATH = "C:"+File.separator+"sourtify" + File.separator; // 저장 위치

    /**
     * src 문자열에 target 문자열이 몇번 나타나는지 카운트 체크
     * @param src
     * @param target
     * @return count
     */
    public static int countChk(String src, String target) {
        int count = 0; // 찾은 횟수
        int pos = 0; // 찾기 시작할 위치

        while (true) {
            pos = src.indexOf(target, pos);
            if (pos != -1) {
                count++;
                pos += target.length();
            } else {
                break;
            }
        }
        return count;
    }

    public static void main(String[] args) throws Exception {
        String filepath = "C:" + File.separator + "sourtify" + File.separator + "gridjs_func.txt";
        System.out.println(filepath);
        BufferedReader br = new BufferedReader(
                new FileReader(filepath)
        );

        String keyword = "grid.";
        String funcStartSep = "(";
        String line;

        PrintWriter writer = new PrintWriter(new FileWriter(SAVE_PATH + "useGridJS_function" + ".txt", false));

        List<String> list = new ArrayList<>();

        while((line = br.readLine()) != null) {
            String tline = line.trim().replaceAll(" ", "");
            String lowerLine = tline.toLowerCase(Locale.US); // 소문자 치환 후 검색
            int count = countChk(lowerLine, keyword);
//            System.out.println(tline + "\t\t grid. count : " + count);

            int pos = 0; // 시작 위치
            int sepPos = 0;
            for (int i = 0; i < count; ++i) {
                pos = lowerLine.indexOf(keyword, pos);
                if (pos != -1) {
                    sepPos = lowerLine.indexOf(funcStartSep, pos);
                    // 메서드 사용 시 "("가 20자 이내로 나타날 경우만 함수로 생각하겠다
                    if (sepPos == -1 || pos + 20 < sepPos) {
                        sepPos = pos;
                        continue;
                    }
//                    System.out.println("grid pos : " + pos + ", sepPos : " + sepPos);
                    String func = tline.substring(pos + keyword.length(), sepPos);
//                    System.out.println("func : " + func);
                    list.add(func);

                    pos += keyword.length();
                }
            }
        }

        br.close();


        // 중복제거 시작
        Set<String> set = new HashSet<String>(list);
        // Set을 List로 변경
        List<String> newList = new ArrayList<String>(set);

        // list iterator
        for (String name : newList) {
            System.out.println("Use Function : " + name);
            writer.write(name + "\r\n");
        }

        writer.close();
    }
}
