/* global malarkey:false, moment:false */
(function() {
  'use strict';

  angular
    .module('web')
    // .constant('malarkey', malarkey)
    .constant('SERVER_PATH', 'http://localhost:8080')
    .constant('moment', moment);

})();
