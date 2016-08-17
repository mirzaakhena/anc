(function() {
    'use strict';

    angular
        .module('web')
        .controller('RawMaterialController', RawMaterialController);

    /** @ngInject */
    function RawMaterialController($uibModal, $state, $scope, RawMaterialService) {

        $scope.create = create;

        function reload() {
            $scope.rawMaterials = RawMaterialService.query();
        }

        function create() {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/rawmaterial/input/input.html',
                controller: 'RawMaterialInputController',
            }).result.then(reload);
        }

        reload();

    }


})();
