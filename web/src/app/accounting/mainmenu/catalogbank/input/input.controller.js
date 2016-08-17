(function() {
    'use strict';

    angular
        .module('web')
        .controller('CatalogBankInputController', CatalogBankInputController);

    /** @ngInject */
    function CatalogBankInputController($uibModal, $uibModalInstance, $scope, CatalogBankService) {

        $scope.submit = submit;
        $scope.cancel = cancel;

        function submit() {
            CatalogBankService.save($scope.catalogbank, close);
        }

        function close() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

    }


})();
