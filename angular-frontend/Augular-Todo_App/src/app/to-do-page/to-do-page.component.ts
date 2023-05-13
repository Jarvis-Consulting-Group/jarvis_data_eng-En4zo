import { Component } from '@angular/core';

@Component({
  selector: 'app-to-do-page',
  templateUrl: './to-do-page.component.html',
  styleUrls: ['./to-do-page.component.css']
})
export class ToDoPageComponent {
  newToDo = "";
  todos : string[] = [];

  newElement = "";
  index : number | null = null;
  addToDo() {
    if(this.newToDo){
      this.todos.push(this.newToDo);
      this.newToDo = "";
    }
    console.log(this.todos);
  }

  removeToDo(){
    this.todos.pop();
  }
  editToDo(){
    if(this.index != null && this.index < this.todos.length){
      this.todos[this.index] = this.newElement;
    }else{
      console.log("Index out of range.");
      console.log(this.index);
      console.log(this.newElement);
    }
  }
  
}
