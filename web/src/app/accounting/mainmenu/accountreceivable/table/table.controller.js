(function() {
    'use strict';

    angular
        .module('web')
        .controller('AccountReceivableTableController', AccountReceivableTableController);

    /** @ngInject */
    function AccountReceivableTableController($uibModal, $state, $scope, AccountReceivableService) {

        $scope.pay = pay;

        function reload() {
            $scope.items = AccountReceivableService.query();
        }

        function pay(item) {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/accountreceivable/input/input.html',
                controller: 'AccountReceivableInputController',
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
