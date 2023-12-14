describe('Página de inicio', () => {
  beforeEach(() => {
    cy.visit('/') // Visita la página de inicio de tu aplicación
    cy.get('app-navbar li').eq(2).click();
    cy.visit('#/places')
  })

  it('debería mostrar el Complejo Histórico Cultural Manzana de las Luces', () => {
    cy.contains("Complejo Histórico Cultural Manzana de las Luces").should('exist');
  })
})