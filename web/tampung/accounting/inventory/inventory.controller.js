// (function() {
//     'use strict';

//     angular
//         .module('web')
//         .controller('InventoryController', InventoryController);

//     /** @ngInject */
//     function InventoryController($scope, account, InventoryChildService, $uibModal, $uibModalInstance) {

//         $scope.accountName = account.name;
//         $scope.ok = ok;
//         $scope.onSubAccountClicked = onSubAccountClicked;

//         $scope.datePicker = {
//             startDate: moment()
//         };

//         $scope.opts = {
//             singleDatePicker: true,
//             locale: { format: 'D/M/YY' }
//         }

//         function reload() {
//             var d1 = moment($scope.datePicker).format("DD-MM-YYYY");
//             var inventories = InventoryChildService.query({
//                 'accountId': account.id,
//                 'date': d1,
//             }, null, function() {
//                 $scope.inventories = inventories;
//             });
//         }

//         $scope.$watch('datePicker', reload, false);

//         function onSubAccountClicked(account) {

//             $uibModal.open({
//                 templateUrl: 'app/accounting/inventory/detail/inventory-detail.html',
//                 controller: 'InventoryDetailController',
//                 size:'lg',
//                 resolve: {
//                     account: function() {
//                         return account;
//                     }
//                 }
//             });


//         }

//         function ok() {
//             $uibModalInstance.close();
//         };

//         function cancel() {
//             $uibModalInstance.dismiss();
//         };

//     }
// })();
