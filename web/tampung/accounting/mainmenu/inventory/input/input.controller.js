(function() {
    'use strict';

    angular
        .module('web')
        .controller('InputInventoryController', InputInventoryController);

    /** @ngInject */
    function InputInventoryController($uibModal, $uibModalInstance, $scope, InventoryUnassignedService, InventoryBalanceService) {

        $scope.submit = submit;
        $scope.cancel = cancel;

        $scope.onJournalSelected = onJournalSelected;

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
            var journals = InventoryUnassignedService.query({
                'date': d1
            }, null, function() {
                $scope.journals = journals;
            });
        }

        function onJournalSelected() {
            $scope.accounts = [];

            for (var i = 0; i < $scope.selectedJournal.accounts.length; i++) {

                $scope.accounts.push({
                    code: $scope.selectedJournal.accounts[i].code,
                    name: $scope.selectedJournal.accounts[i].name,
                    pos: $scope.selectedJournal.accounts[i].pos,
                    quantity: 1,
                });

            }
        }

        function submit() {

            var accounts = [];

            for (var i = 0; i < $scope.accounts.length; i++) {
                accounts.push({
                    code: $scope.accounts[i].code,
                    quantity: $scope.accounts[i].quantity
                });
            }

            InventoryBalanceService.save({
                journalId: $scope.selectedJournal.id,
                accounts: accounts
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
