import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { TokenService } from '../services/token.service';

@Injectable({
  providedIn: 'root',
})

export class AuthGuard implements CanActivate {

  constructor(
    private tokenService: TokenService,
    private router: Router,
    ) {}

  canActivate(): boolean | UrlTree {

    return this.tokenService.isLoggedIn || this.router.parseUrl('login');
  }
}
