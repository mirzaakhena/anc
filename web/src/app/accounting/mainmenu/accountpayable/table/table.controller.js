(function() {
    'use strict';

    angular
        .module('web')
        .controller('AccountPayableTableController', AccountPayableTableController);

    /** @ngInject */
    function AccountPayableTableController($uibModal, $state, $scope, AccountPayableService) {

        $scope.pay = pay;

        function reload() {
            $scope.items = AccountPayableService.query();
        }

        function pay(item) {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/accountpayable/input/input.html',
                controller: 'AccountPayableInputController',
                resolve: {
                    account: function() {
                        return item;
                    }
                }
            }).result.then(reload);
        }

        reload();

    }


})();
