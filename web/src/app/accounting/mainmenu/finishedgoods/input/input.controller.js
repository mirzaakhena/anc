(function() {
    'use strict';

    angular
        .module('web')
        .controller('FinishedGoodsInputController', FinishedGoodsInputController);

    /** @ngInject */
    function FinishedGoodsInputController($uibModal, $http, SERVER_PATH, $uibModalInstance, $scope, FinishedGoodsService, UnitService, RawMaterialService) {

        $scope.submit = submit;
        $scope.cancel = cancel;
        $scope.getRawMaterial = getRawMaterial;
        $scope.addRawMaterial = addRawMaterial;
        $scope.removeRawMaterial = removeRawMaterial;

        $scope.rawMaterials = [];

        function reload() {
            $scope.units = UnitService.query();
        }

        function getRawMaterial(name) {
            return $http.get(SERVER_PATH + '/rawmaterial', {
                params: {
                    name: name
                }
            }).then(function(response) {
                return response.data;
            });
        }

        function addRawMaterial() {
            $scope.rawMaterials.push({
                code: $scope.selectedRawMaterial.code,
                quantity: $scope.quantitySelectedRawMaterial,
                name: $scope.selectedRawMaterial.name,
                unitName: $scope.selectedRawMaterial.unitName
            });
            $scope.selectedRawMaterial = null;
            $scope.quantitySelectedRawMaterial = null;
        }

        function removeRawMaterial(index) {
            $scope.rawMaterials.splice(index, 1);
        }

        function submit() {

            var rawMaterialDetails = [];

            for (var i = 0; i < $scope.rawMaterials.length; i++) {
                rawMaterialDetails.push({
                    rawMaterialCode: $scope.rawMaterials[i].code,
                    quantity: $scope.rawMaterials[i].quantity
                });
            }

            FinishedGoodsService.save({
                name: $scope.finishedGoods.name,
                description: $scope.finishedGoods.description,
                unitId: $scope.selectedUnit.id,
                minimalQuantity: $scope.finishedGoods.minimalQuantity,
                labour: $scope.finishedGoods.labour,
                overhead: $scope.finishedGoods.overhead,
                salePrice: $scope.finishedGoods.salePrice,
                rawMaterialDetails: rawMaterialDetails
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
