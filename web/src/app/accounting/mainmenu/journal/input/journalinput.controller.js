(function() {
    'use strict';

    angular
        .module('web')
        .controller('JournalInputController', JournalInputController);

    /** @ngInject */
    function JournalInputController($scope, $uibModal, AccountService, JournalService, $uibModalInstance) {

        $scope.createSide = createSide;
        $scope.createJournal = createJournal;

        $scope.journals = [];

        $scope.removeJournal = removeJournal;

        $scope.isDebet = isDebet;

        $scope.isCredit = isCredit;

        function createSide(side) {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/journal/input/journal-input-side.html',
                controller: 'JournalInputSideController',
                resolve: {
                    side: function() {
                        return side;
                    }
                }
            }).result.then(function(data) {
                $scope.journals.push(data);
            });
        }

        function removeJournal(index) {
            $scope.journals.splice(index, 1);
        }

        function createJournal() {

            if(typeof $scope.description === 'undefined' || $scope.description ===''){
                alert('description masih kosong. Silakan diisi terlebih dahulu');
                return;
            }

            var transactions = [];

            for (var i = 0; i < $scope.journals.length; i++) {
                transactions.push({
                    code: $scope.journals[i].code,
                    amount: $scope.journals[i].amount,
                    quantity: $scope.journals[i].quantity
                });
            }

            JournalService.save({
                description: $scope.description,
                transactions: transactions
            }, closeModal);

        }

        function closeModal() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

        function isDebet(journal) {
            return (journal.side === 'Activa' && journal.amount > 0) || (journal.side ==='Passiva' && journal.amount < 0);
        }

        function isCredit(journal) {
            return (journal.side === 'Passiva' && journal.amount > 0) || (journal.side ==='Activa' && journal.amount < 0);
        }

    }

})();
