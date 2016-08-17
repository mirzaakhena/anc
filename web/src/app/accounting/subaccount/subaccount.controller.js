(function() {
    'use strict';

    angular
        .module('web')
        .controller('SubAccountController', SubAccountController);

    /** @ngInject */
    function SubAccountController($scope, account, AccountBalanceChildService, $uibModal, $uibModalInstance) {

        $scope.accountName = account.name;
        $scope.onSubAccountClicked = onSubAccountClicked;
        $scope.ok = ok;

        $scope.datePicker = {
            startDate: moment()
        };

        $scope.opts = {
            singleDatePicker: true,
            locale: { format: 'DD/MM/YY' }
        };

        $scope.$watch('datePicker', reload, false);

        function reload() {
            var d1 = moment($scope.datePicker).format("DD-MM-YYYY");
            var accountBalances = AccountBalanceChildService.query({
                'accountId': account.id,
                'date': d1
            }, null, function() {
                $scope.accountBalances = accountBalances;
            });
        }

        function onSubAccountClicked(account) {

            $uibModal.open({
                templateUrl: 'app/accounting/generalledger/general-ledger.html',
                controller: 'GeneralLedgerController',
                size: 'lg',
                resolve: {
                    account: function() {
                        return account;
                    }
                }
            });

        }

        function ok() {
            $uibModalInstance.close();
        };

    }
})();
