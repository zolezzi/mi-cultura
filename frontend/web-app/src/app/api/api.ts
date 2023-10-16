export * from './service/accountController.service';
import { AccountControllerService } from './service/accountController.service';
export * from './service/adminController.service';
import { AdminControllerService } from './service/adminController.service';
import { PlaceControllerService } from './service/placeController.service';
export * from './service/userController.service';
import { UserControllerService } from './service/userController.service';
export const APIS = [AccountControllerService, AdminControllerService, UserControllerService, PlaceControllerService];
