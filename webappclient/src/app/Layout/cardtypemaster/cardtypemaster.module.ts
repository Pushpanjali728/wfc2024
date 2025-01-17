import { NgModule } from '@angular/core';
import { CommonModule} from '@angular/common';
import { TabModule } from '../Components/tabs/tab1.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CardtypemasterComponent } from './cardtypemaster.component';
import { CardtypeMasterRoutingModule } from './cardtypemaster-routing.module';
import { CardtypedetailComponent } from './cardtypedetail';
import { CardtypeentryComponent } from './cardtypedetail/cardtypeentry/cardtypeentry.component';
import { CardtypeHistoryComponent } from './cardtypedetail/cardtypehistory/cardtypehistory.component';
import { TableModule } from 'primeng/table';
import {CalendarModule} from 'primeng/calendar';
import {SliderModule} from 'primeng/slider';
import {DialogModule} from 'primeng/dialog';
import {MultiSelectModule} from 'primeng/multiselect';
import {ContextMenuModule} from 'primeng/contextmenu';
import {ButtonModule} from 'primeng/button';
import {ToastModule} from 'primeng/toast';
import {InputTextModule} from 'primeng/inputtext';
import {ProgressBarModule} from 'primeng/progressbar';
import {DropdownModule} from 'primeng/dropdown';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { UserService } from 'src/app/DemoPages/UserPages/_services/user.service';
import { authInterceptorProviders } from 'src/app/DemoPages/UserPages/_helpers/auth.interceptor';
import {AutoCompleteModule} from 'primeng/autocomplete';
import { ToolbarModule } from 'primeng/toolbar';




@NgModule({
  imports: [
    CommonModule,
    CardtypeMasterRoutingModule,
    TabModule,
    NgbModule,
    TableModule,
    CalendarModule,
		SliderModule,
		DialogModule,
		MultiSelectModule,
		ContextMenuModule,
		DropdownModule,
		ButtonModule,
		ToastModule,
    InputTextModule,
    ProgressBarModule,
    HttpClientModule,
    FormsModule,
    AutoCompleteModule,
    ToolbarModule
    ],
  exports: [
    CardtypemasterComponent
  ],
  declarations: [
    CardtypemasterComponent,CardtypedetailComponent,CardtypeentryComponent,CardtypeHistoryComponent
  ],
  providers: [ UserService, authInterceptorProviders],
})
export class CardtypeMasterModule { }
