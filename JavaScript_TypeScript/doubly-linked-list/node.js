"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.listNode = void 0;
var listNode = /** @class */ (function () {
    function listNode(Value) {
        this.Value = Value;
        this.NextNode = null;
        this.PrevNode = null;
    }
    return listNode;
}());
exports.listNode = listNode;
