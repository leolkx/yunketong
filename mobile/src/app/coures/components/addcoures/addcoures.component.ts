import { Component, OnInit } from '@angular/core'; 
import { ModalController } from '@ionic/angular'; 
import { PopoverController } from '@ionic/angular';
import { CouresnumberComponent } from '../couresnumber/couresnumber.component';

import { CreadcourseComponent } from '../creadcourse/creadcourse.component'; 

import { PickerController } from '@ionic/angular';
@Component({
  selector: 'app-addcoures',
  templateUrl: './addcoures.component.html',
  styleUrls: ['./addcoures.component.scss'],
})
export class AddcouresComponent implements OnInit {

  //  public dependentColumns:any = [
  //   {
  //     options: [
  //       { text: '1', value: '1' },
  //       { text: '2', value: '2' },
  //       { text: '3', value: '3' }
  //     ]
  //   },{
  //     options: [
  //       { text: '1-1', value: '1-1', parentVal: '1' },
  //       { text: '1-2', value: '1-2', parentVal: '1' },
  //       { text: '2-1', value: '2-1', parentVal: '2' },
  //       { text: '2-2', value: '2-2', parentVal: '2' },
  //       { text: '3-1', value: '3-1', parentVal: '3' }
  //     ]
  //   }];
  
  public currentPopover:any = null;
  constructor(public modalController: ModalController,
    public popoverController: PopoverController,
    private pickerController: PickerController) {

  }

  async presentModal() {
    const modal = await this.modalController.create({
      component: CouresnumberComponent
    });
    
    await modal.present();
    await modal.onDidDismiss().then(()=>{
      this.popoverController.dismiss();
    }
    )
  } 
  async creatcourse() {
    const modal = await this.modalController.create({
      component: CreadcourseComponent
    });
    
    await modal.present();
    await modal.onDidDismiss().then(()=>{
      this.popoverController.dismiss();
    }
    )
  } 
  ngOnInit() {}
  async presentPopover(ev: any) { 
    const popover = await this.popoverController.create({
      component: AddcouresComponent,
      event: ev,
      backdropDismiss:true,
      translucent: true
    });
    this.currentPopover=popover;
     
    await popover.present();
    await popover.onDidDismiss().then((response)=>{
        console.log(111)
        //这里到时候刷新页面
    })
  } 
  // public async openPicker() {
  //   const picker = await this.pickerController.create({
  //     columns: this.getColumns(),
  //     buttons: [
  //       {
  //         text: '取消',
  //         role: 'cancel'
  //       },
  //       {
  //         text: '确定',
  //         handler: (value) => {
  //           console.log(`${value['col-0'].text}`);
  //         }
  //       }
  //     ]
  //   });

  //   await picker.present();
  // }

  // private getColumns() {
  //   // ion-pick 有个问题，create方法接收的参数中columns 不能直接给对象字面量，必须每次调用create时动态创建一个新对象，即时将这个对象保存为组件对象的属性xxx，这里通过return this.xxx，这样的方式也不行，会导致第二次打开时样式不正常。
  //   return [
  //     {
  //       name: 'col-0',
  //       options: [
  //         {
  //           text: "选项1",
  //           value: 0
  //         },
  //         {
  //           text: "选项2",
  //           value: 1
  //         },
  //         {
  //           text: "选项3",
  //           value: 2
  //         }
  //       ]
  //     }
  //   ];
  // }
}
