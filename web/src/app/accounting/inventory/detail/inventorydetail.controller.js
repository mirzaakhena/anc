// (function() {
//     'use strict';

//     angular
//         .module('web')
//         .controller('InventoryDetailController', InventoryDetailController);

//     /** @ngInject */
//     function InventoryDetailController($scope, account, InventoryService, $uibModalInstance) {

//         $scope.accountName = account.name;
//         $scope.ok = ok;

//         $scope.datePicker = {
//             startDate: moment().subtract(6, 'days'),
//             endDate: moment()
//         };

//         $scope.opts = {
//             singleDatePicker: false,
//             locale: {
//                 format: 'D/M/YY'
//             },
//             ranges: {
//                 'Today': [moment().subtract(0, 'days'), moment().subtract(0, 'days')],
//                 'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
//                 'Last 7 Days': [moment().subtract(6, 'days'), moment()],
//                 'Last 30 Days': [moment().subtract(29, 'days'), moment()]
//             }
//         }

//         function reload() {
//             if ($scope.datePicker.startDate === null || $scope.datePicker.endDate === null) {
//                 return;
//             }
//             var d1 = moment($scope.datePicker.startDate).format("DD-MM-YYYY");
//             var d2 = moment($scope.datePicker.endDate).format("DD-MM-YYYY");
//             $scope.inventories = InventoryService.query({
//                 'accountId': account.id,
//                 'date': [d1, d2]
//             });
//         }

//         $scope.$watch('datePicker', reload, false);


//         function ok() {
//             $uibModalInstance.close();
//         };

//         function cancel() {
//             $uibModalInstance.dismiss();
//         };

//     }
// })();
