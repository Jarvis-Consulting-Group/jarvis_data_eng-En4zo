export class listNode<T> {
    Value : T;
    NextNode : listNode<T> | null;
    PrevNode : listNode<T> | null;

    constructor(Value : T){
        this.Value = Value;
        this.NextNode = null;
        this.PrevNode = null;
    }

}