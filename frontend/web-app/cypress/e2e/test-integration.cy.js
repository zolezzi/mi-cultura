describe('Página de inicio', () => {
  beforeEach(() => {
    cy.visit('/') // Visita la página de inicio de tu aplicación
    cy.get('app-navbar li').eq(2).click();
    cy.visit('#/places')
  })

  it('debería mostrar el Instituto Nacional de Antropología y Pensamiento Latinoamericano', () => {
    cy.contains("Instituto Nacional de Antropología y Pensamiento Latinoamericano2").should('exist');
  })
})