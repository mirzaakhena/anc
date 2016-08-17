(function() {
    'use strict';

    angular
        .module('web')
        .controller('WIPInputController', WIPInputController);

    /** @ngInject */
    function WIPInputController($uibModal, SERVER_PATH, $http, $uibModalInstance, $scope, WIPStartService) {

        $scope.submit = submit;
        $scope.cancel = cancel;

        $scope.firsttime = false;

        function submit() {
            WIPStartService.save({

                wipCode: $scope.fgSelected.code,
                productionCode: $scope.productionCode,
                newQuantity: parseInt($scope.quantityProduksi),
                prevQuantity: $scope.prevQuantityProduksi,
                prevCostRawMaterial: $scope.prevCostRM,
                prevCostLabour: $scope.prevCostLB,
                prevCostOverhead: $scope.prevCostOH

            }, close);
        }

        function close() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

        $scope.getFinishGoods = function(name) {
            return $http.get(SERVER_PATH + '/workinprocess', {
                params: {
                    name: name
                }
            }).then(function(response) {
                return response.data;
            });

        }

        $scope.onSelect = function() {
            $http.get(SERVER_PATH + '/workinprocessbalance/last/' + $scope.fgSelected.id)
                .then(function(response) {
                    console.log(response.data);
                    $scope.firsttime = response.data.length === 0;
                });
        }

    }


})();
