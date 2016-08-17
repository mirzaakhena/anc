(function() {
    'use strict';

    angular
        .module('web')
        .controller('WIPContinueController', WIPContinueController);

    /** @ngInject */
    function WIPContinueController($uibModal, $uibModalInstance, $scope, WIPService) {

        $scope.submit = submit;
        $scope.cancel = cancel;

        function submit() {
            WIPService.save($scope.unit, close);
        }

        function close() {
            $uibModalInstance.close();
        };

        function cancel() {
            $uibModalInstance.dismiss();
        };

    }


})();
