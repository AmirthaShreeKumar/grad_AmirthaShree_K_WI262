# StudentManagementApp

Simple Angular SPA for managing student records. Features:

- Login screen with role selection (admin or staff)
- Admin users can add, edit and delete students
- Staff users can only view the list of students
- In-memory data store via a service (no backend)

**Credentials:**

- For **admin** role, any username works but password must be `admin123`. This simple check prevents anyone from merely selecting "admin" and gaining full control; the password acts as the single credential that distinguishes an actual administrator from a staff user.
- For **staff** role, any username/password combination is accepted.

This repo can be cloned and run locally as described below.

This project was generated using [Angular CLI](https://github.com/angular/angular-cli) version 21.2.0.

## Development server

To start a local development server, run:

```bash
ng serve
```

Once the server is running, open your browser and navigate to `http://localhost:4200/`. The application will automatically reload whenever you modify any of the source files.

## Code scaffolding

Angular CLI includes powerful code scaffolding tools. To generate a new component, run:

```bash
ng generate component component-name
```

For a complete list of available schematics (such as `components`, `directives`, or `pipes`), run:

```bash
ng generate --help
```

## Building

To build the project run:

```bash
ng build
```

This will compile your project and store the build artifacts in the `dist/` directory. By default, the production build optimizes your application for performance and speed.

## Running unit tests

To execute unit tests with the [Vitest](https://vitest.dev/) test runner, use the following command:

```bash
ng test
```

## Running end-to-end tests

For end-to-end (e2e) testing, run:

```bash
ng e2e
```

Angular CLI does not come with an end-to-end testing framework by default. You can choose one that suits your needs.

## Additional Resources

For more information on using the Angular CLI, including detailed command references, visit the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.
