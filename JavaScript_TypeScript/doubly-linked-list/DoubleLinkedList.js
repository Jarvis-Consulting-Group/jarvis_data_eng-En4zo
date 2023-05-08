"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var node = require("./node");
var DoubleLinkedList = /** @class */ (function () {
    function DoubleLinkedList() {
        this.head = null;
        this.tail = null;
    }
    DoubleLinkedList.prototype.push = function (Value) {
        var newNode = new node.listNode(Value);
        if (!this.head) {
            this.head = newNode;
            this.tail = newNode;
        }
        else {
            if (this.tail) {
                this.tail.NextNode = newNode;
            }
            newNode.PrevNode = this.tail;
            this.tail = newNode;
        }
    };
    DoubleLinkedList.prototype.pop = function () {
        if (!this.tail) {
            return null;
        }
        var value = this.tail.Value;
        this.tail = this.tail.PrevNode;
        if (this.tail) {
            this.tail.NextNode = null;
        }
        else {
            this.head = null;
        }
        return value;
    };
    DoubleLinkedList.prototype.shift = function () {
        if (!this.head) {
            return null;
        }
        var value = this.head.Value;
        this.head = this.head.NextNode;
        if (this.head) {
            this.head.PrevNode = null;
        }
        else {
            this.tail = null;
        }
        return value;
    };
    DoubleLinkedList.prototype.unshift = function (Value) {
        var newNode = new node.listNode(Value);
        if (!this.head) {
            this.head = newNode;
            this.tail = newNode;
        }
        else {
            newNode.NextNode = this.head;
            this.head.PrevNode = newNode;
            this.head = newNode;
        }
    };
    return DoubleLinkedList;
}());
var list = new DoubleLinkedList();
list.push(1);
list.push(2);
list.push(3);
console.log(list.pop()); // Output: 3
list.unshift(0);
console.log(list.shift()); // Output: 0
