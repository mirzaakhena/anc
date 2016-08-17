(function() {
    'use strict';

    angular
        .module('web')
        .controller('UnitTableController', UnitTableController);

    /** @ngInject */
    function UnitTableController($uibModal, $state, $scope, UnitService) {

        $scope.create = create;

        function reload() {
            $scope.units = UnitService.query();
        }

        function create() {
            $uibModal.open({
                templateUrl: 'app/accounting/mainmenu/unit/input/input.html',
                controller: 'UnitInputController',
            }).result.then(reload);
        }

        reload();

    }


})();
