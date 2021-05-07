import { Component, OnInit,ChangeDetectionStrategy, ChangeDetectorRef,OnDestroy,Input,Output,EventEmitter} from '@angular/core';

@Component({
  selector: 'app-reg-usermsg',
  templateUrl: './reg-usermsg.component.html',
  styleUrls: ['./reg-usermsg.component.scss'],
})
export class RegUsermsgComponent implements OnInit {

@Output() public outer = new EventEmitter<any>();
  public user:any={
    userName:'',
    userAge:'',
    school:''
  }
  constructor() { }

  ngOnInit() {}
  register()
  {
    //提交数据注册

    //他
    this.outer.emit(1);
  }
}
