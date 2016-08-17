(function() {
    'use strict';

    angular
        .module('web')
        .controller('AccountController', AccountController);

    /** @ngInject */
    function AccountController($scope, AccountBalanceService, $uibModal) {

        $scope.onAccountClicked = onAccountClicked;
        $scope.onSubAccountClicked = onSubAccountClicked;

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
            var accountBalances = AccountBalanceService.query({
                'date': d1
            }, null, function() {
                $scope.accountBalances = accountBalances;
            });
        }

        function onAccountClicked(account) {

            if (account.accountType == 'FINAL_ACCOUNT') {

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


            } else {

                $uibModal.open({
                    templateUrl: 'app/accounting/mainmenu/account/detail/account-balance.html',
                    controller: 'AccountBalanceController',
                    resolve: {
                        account: function() {
                            return account;
                        }
                    }
                });


            }

        }

        function onSubAccountClicked(account) {

            $uibModal.open({
                templateUrl: 'app/accounting/subaccount/subaccount.html',
                controller: 'SubAccountController',
                resolve: {
                    account: function() {
                        return account;
                    }
                }
            });

        }

        $scope.create = function() {
            console.log('test');
        }


    }
})();
