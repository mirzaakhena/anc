(function() {
    'use strict';

    angular
        .module('web')
        .controller('FinishedGoodsController', FinishedGoodsController);

    /** @ngInject */
    function FinishedGoodsController($uibModal, $state, $scope, FinishedGoodsService) {

        $scope.create = create;

        function reload() {
            $scope.listOfFinishGoods = FinishedGoodsService.query();            
        }

        function create() {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/finishedgoods/input/input.html',
                controller: 'FinishedGoodsInputController',
                size: 'lg'
            }).result.then(reload);
        }

        reload();

    }


})();
