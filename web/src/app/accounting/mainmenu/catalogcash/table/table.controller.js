(function() {
    'use strict';

    angular
        .module('web')
        .controller('CatalogCashController', CatalogCashController);

    /** @ngInject */
    function CatalogCashController($uibModal, $state, $scope, CatalogCashService) {

        $scope.create = create;

        function reload() {
            $scope.catalogcashes = CatalogCashService.query();
        }

        function create() {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/catalogcash/input/input.html',
                controller: 'CatalogCashInputController',
            }).result.then(reload);
        }

        reload();

    }


})();
