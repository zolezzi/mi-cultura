import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Configuration } from './configuration';

import { AccountControllerService } from './service/accountController.service';
import { AdminControllerService } from './service/adminController.service';
import { EventControllerService } from './service/eventController.service';
import { PlaceControllerService } from './service/placeController.service';
import { ProxyControllerService } from './service/proxyController.service';
import { ReviewControllerService } from './service/reviewController.service';
import { UserControllerService } from './service/userController.service';

@NgModule({
  imports:      [ CommonModule, HttpClientModule ],
  declarations: [],
  exports:      [],
  providers: [
    AccountControllerService,
    AdminControllerService,
    EventControllerService,
    PlaceControllerService,
    ProxyControllerService,
    ReviewControllerService,
    UserControllerService ]
})
export class ApiModule {
    public static forRoot(configurationFactory: () => Configuration): ModuleWithProviders<ApiModule> {
        return {
            ngModule: ApiModule,
            providers: [ { provide: Configuration, useFactory: configurationFactory } ]
        }
    }

    constructor( @Optional() @SkipSelf() parentModule: ApiModule) {
        if (parentModule) {
            throw new Error('ApiModule is already loaded. Import your base AppModule only.');
        }
    }
}
