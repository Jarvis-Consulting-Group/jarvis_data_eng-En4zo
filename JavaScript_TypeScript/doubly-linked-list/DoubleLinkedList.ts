import * as node from "./node";

class DoubleLinkedList<T>{
    private head : node.listNode<T> | null;
    private tail : node.listNode<T> | null;

    constructor(){
        this.head = null;
        this.tail = null;
    }

    push(Value : T):void{
        const newNode = new node.listNode(Value);

        if(!this.head){
            this.head = newNode;
            this.tail = newNode;
        }else{
            if(this.tail){
                this.tail.NextNode = newNode;
            }
            newNode.PrevNode = this.tail;
            this.tail = newNode;
        }
    }

    pop(): T | null{
        if(!this.tail){
            return null;
        }
        const value = this.tail.Value;
        this.tail = this.tail.PrevNode;
        if(this.tail){
            this.tail.NextNode = null;
        }else{
            this.head = null;
        }
        return value;
    }
    shift(): T | null{
        if(!this.head){
            return null;
        }
        const value = this.head.Value;
        this.head = this.head.NextNode;

        if(this.head){
            this.head.PrevNode = null;
        }else{
            this.tail = null;
        }

        return value;
    }
    unshift(Value : T): void{
        const newNode = new node.listNode(Value);

        if(!this.head){
            this.head = newNode;
            this.tail = newNode;
        }else{
            newNode.NextNode = this.head;
            this.head.PrevNode = newNode;
            this.head = newNode;
        }

    }



}

const list = new DoubleLinkedList<number>();
list.push(1);
list.push(2);
list.push(3);
console.log(list.pop()); // Output: 3
list.unshift(0);
console.log(list.shift()); // Output: 0