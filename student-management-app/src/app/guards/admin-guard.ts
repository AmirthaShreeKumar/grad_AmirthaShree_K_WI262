import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { Auth } from '../services/auth';

export const adminGuard: CanActivateFn = (route, state) => {

  const auth = inject(Auth);
  const router = inject(Router);

  if(auth.getRole() === 'admin'){
    return true;
  }

  alert("Only Admin can access this page");
  router.navigate(['/students']);
  return false;

};