import { Routes } from '@angular/router';

import { LoginComponent } from './pages/login/login.component';
import { MainLayoutComponent } from './pages/layout/main-layout.component';
import { ClientesListComponent } from './pages/clientes/clientes-list.component';
import { PedidosListComponent } from './pages/pedidos/pedidos-list.component';
import { AuthGuard } from './core/auth/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },

  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'clientes', component: ClientesListComponent },
      { path: 'pedidos', component: PedidosListComponent },
      { path: '', redirectTo: 'clientes', pathMatch: 'full' }
    ]
  },

  { path: '**', redirectTo: 'login' }
];
