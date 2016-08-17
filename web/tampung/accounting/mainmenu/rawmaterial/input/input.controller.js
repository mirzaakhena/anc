(function() {
    'use strict';

    angular
        .module('web')
        .controller('RawMaterialInputController', RawMaterialInputController);

    /** @ngInject */
    function RawMaterialInputController($uibModal, $uibModalInstance, $scope, RawMaterialService, UnitService) {

        $scope.submit = submit;
        $scope.cancel = cancel;

        function reload() {
            $scope.units = UnitService.query();
        }

        function submit() {

            console.log($scope.rawMaterial.minimalQuantity);

            RawMaterialService.save({
                name: $scope.rawMaterial.name,
                description: $scope.rawMaterial.description,
                unitId: $scope.selectedUnit.id,
                minimalQuantity: $scope.rawMaterial.minimalQuantity
            }, close);
        }

        function close() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

        reload();

    }


})();
