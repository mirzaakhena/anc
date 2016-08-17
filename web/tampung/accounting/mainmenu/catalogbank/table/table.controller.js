(function() {
    'use strict';

    angular
        .module('web')
        .controller('CatalogBankController', CatalogBankController);

    /** @ngInject */
    function CatalogBankController($uibModal, $state, $scope, CatalogBankService) {

        $scope.create = create;

        function reload() {
            $scope.catalogbanks = CatalogBankService.query();
        }

        function create() {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/catalogbank/input/input.html',
                controller: 'CatalogBankInputController',
            }).result.then(reload);
        }

        reload();

    }


})();
