/**
 * This is test.js
 */

function MyObject(id) {
  this.variable1 = "Hello World";
  this.variable2 = [1,2,3,4];

  this.method1 = function() {
    return this.variable1;
  }

  this.method2 = function() {
    var x = this.variable2.filter(x => x > 2).join(' ');
    console.log(x);
  }

  this.currency = function() {
    console.log("$");
  }

  this.bitCoin = function() {
    console.log("GAZAAAAAAA!");
  }
}

MyObject.prototype.protoFunction = function() {
  console.log("this is prototype function");
}

MyObject.prototype.protoFunction2 = function() {
  return 123;
}

console.log("Hello JS");

function anyfunction() {
  console.log("anyfunction");
}