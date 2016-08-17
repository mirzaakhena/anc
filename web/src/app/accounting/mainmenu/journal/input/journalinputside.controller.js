(function() {
    'use strict';

    angular
        .module('web')
        .controller('JournalInputSideController', JournalInputSideController);

    /** @ngInject */
    function JournalInputSideController($scope, side, AccountService, AccountUnitService, AccountBalanceService, $uibModal, $uibModalInstance) {

        $scope.side = side;
        $scope.create = create;
        $scope.cancel = cancel;
        $scope.onAccountSelected = onAccountSelected;
        $scope.onAccountChildSelected = onAccountChildSelected;

        function reload() {

            $scope.accounts = [];

            var accounts = AccountService.query({
                'side': side,
                'level': 3
            }, null, function() {
                for (var i = 0; i < accounts.length; i++) {
                    $scope.accounts.push({
                        id: accounts[i].id,
                        code: accounts[i].code,
                        name: accounts[i].name,
                        hasChild: accounts[i].accountType !== 'FINAL_ACCOUNT',
                        amount: '',
                        childCode: '',
                        type: 1
                    });
                }
            });

        }

        reload();

        function onAccountSelected() {

            $scope.unitName = '';
            $scope.type = null;
            $scope.accountChilds = [];

            var accountChilds = AccountService.query({
                'accountId': $scope.selectedAccount.id

            }, null, function() {
                for (var i = 0; i < accountChilds.length; i++) {
                    $scope.accountChilds.push({
                        id: accountChilds[i].id,
                        code: accountChilds[i].code,
                        name: accountChilds[i].name,
                        hasChild: accountChilds[i].accountType == 'FINAL_ACCOUNT',
                        amount: '',
                        childCode: '',
                        type: 1
                    });
                }
            });

        }

        function onAccountChildSelected() {
            // $scope.selectedAccount.amount = 0;
        }

        function create() {

            var desc = '';

            if ($scope.selectedAccountChild) {
                desc = $scope.selectedAccount.name + ' : ' + $scope.selectedAccountChild.name;

            } else {
                desc = $scope.selectedAccount.name;
            }

            var result = {
                amount: $scope.selectedAccount.amount * $scope.type,
                totalAmount: $scope.selectedAccount.amount,
                code: $scope.selectedAccountChild ? $scope.selectedAccountChild.code : $scope.selectedAccount.code,
                desc: desc,
                side: side
            };
            closeModal(result);
        }

        function closeModal(data) {
            $uibModalInstance.close(data);
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

    }

})();
