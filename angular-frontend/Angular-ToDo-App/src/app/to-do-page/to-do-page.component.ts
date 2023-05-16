import { Component } from '@angular/core';

@Component({
  selector: 'app-to-do-page',
  templateUrl: './to-do-page.component.html',
  styleUrls: ['./to-do-page.component.css']
})
export class ToDoPageComponent {
  todo : string = "";
  todos : string[] = [];

  addToDo(){
    this.todos.push(this.todo);
    console.log(this.todos);
  }

  deleteToDo(index : number){
    this.todos.splice(index,1);
  }

}
