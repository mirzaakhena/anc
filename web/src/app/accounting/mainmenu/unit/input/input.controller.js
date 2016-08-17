(function() {
    'use strict';

    angular
        .module('web')
        .controller('UnitInputController', UnitInputController);

    /** @ngInject */
    function UnitInputController($uibModal, $uibModalInstance, $scope, UnitService) {

        $scope.submit = submit;
        $scope.cancel = cancel;

        function submit() {
            UnitService.save($scope.unit, close);
        }

        function close() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

    }


})();
