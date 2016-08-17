(function() {
    'use strict';

    angular
        .module('web')
        .controller('AccountReceivableInputController', AccountReceivableInputController);

    /** @ngInject */
    function AccountReceivableInputController($uibModal, SERVER_PATH, $http, account, $uibModalInstance, $scope, AccountReceivableService) {

        $scope.submit = submit;
        $scope.cancel = cancel;
        $scope.valueChange = valueChange;

        $scope.total = account.amount;

        $scope.opts = {
            singleDatePicker: true,
            locale: { format: 'DD/MM/YY' }
        };

        $scope.datePicker = {
            startDate: moment().add(30, 'days')
        };

        $scope.cash = {
            account: null,
            amount: null
        };

        $scope.bank = {
            account: null,
            amount: null
        };

        function valueChange() {



        }

        $scope.getCash = function(name) {
            return $http.get(SERVER_PATH + '/catalogcash', {
                params: {
                    name: name
                }
            }).then(function(response) {
                return response.data;
            });
        }

        $scope.getBank = function(name) {
            return $http.get(SERVER_PATH + '/catalogbank', {
                params: {
                    name: name
                }
            }).then(function(response) {
                return response.data;
            });
        }

        function submit() {

            if ((!$scope.cash.account && $scope.cash.amount) || ($scope.cash.account && !$scope.cash.amount)) {
                alert('pilih kas dan input dp untuk kas');
                return;
            }

            if ((!$scope.bank.account && $scope.bank.amount) || ($scope.bank.account && !$scope.bank.amount)) {
                alert('pilih bank dan input dp untuk bank');
                return;
            }

            var cashCode = null;
            if ($scope.cash.account) {
                cashCode = $scope.cash.account.code;
            }

            var bankCode = null;
            if ($scope.bank.account) {
                bankCode = $scope.bank.account.code;
            }

            var cashAmount = 0;
            if ($scope.cash.amount) {
                cashAmount = $scope.cash.amount;
            }

            var bankAmount = 0;
            if ($scope.bank.amount) {
                bankAmount = $scope.bank.amount;
            }

            AccountReceivableService.save({
                id: account.id,
                cash: {
                    amount: parseInt(cashAmount),
                    code: cashCode
                },
                bank: {
                    amount: parseInt(bankAmount),
                    code: bankCode
                },
                nextPaymentDate: moment($scope.datePicker).format("DD-MM-YYYY")
            }, close);

        }

        function close() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

    }


})();
