(function() {
    'use strict';

    angular
        .module('web')
        .controller('CatalogCashInputController', CatalogCashInputController);

    /** @ngInject */
    function CatalogCashInputController($uibModal, $uibModalInstance, $scope, CatalogCashService) {

        $scope.submit = submit;
        $scope.cancel = cancel;

        function submit() {
            CatalogCashService.save($scope.catalogcash, close);
        }

        function close() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

    }


})();
