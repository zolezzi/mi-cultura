import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import { LocalStorageService } from 'ngx-webstorage';
import { CulturaAPIService } from 'src/app/shared/service/cultura-api.service';
import { PlaceControllerService } from 'src/app/api/service/placeController.service';
import { PlaceDTO } from 'src/app/api/model/placeDTO';

@Component({
  selector: 'app-place-search',
  templateUrl: './place-search.component.html',
  styleUrls: ['./place-search.component.css']
})
export class PlaceSearchComponent implements OnInit{

  isAdmin: boolean = false;
  places: PlaceDTO[] = [];
  pageSize = 10;
  pageIndex = 0;
  totalItems = 0;
  role!: string;
  private readonly ACCESS_TOKEN: string = 'ACCESS_TOKEN';
  private readonly ROLE: string = 'ROLE';
  
  constructor(private router: Router, private localStorageService: LocalStorageService, 
    private culturaAPIService :CulturaAPIService, private placeService: PlaceControllerService) {

  }

  ngOnInit(): void {
    this.role = this.localStorageService.retrieve(this.ROLE);
    this.isAdmin = 'ADMIN' == this.role;
    const token = this.localStorageService.retrieve(this.ACCESS_TOKEN);
    
    this.culturaAPIService.getMuseums().subscribe((data) => {
      var placesAPI = data.results;
      this.places = placesAPI.map( (item:any) => {
       var placeI = {} as PlaceDTO;
       placeI.name = item.nombre;
       placeI.description = item.descripcion;
       return placeI;
      });
    });
    this.loadPlaces();
  }

  loadPlaces() {
    this.places = [
      {placeTypeDescription: 'MUSEUM', description: `<p>El Complejo Hist&oacute;rico Cultural Manzana de las Luces depende del Ministerio de Cultura de la Naci&oacute;n.</p>`},
      {placeTypeDescription: 'MUSEUM', description: `<p>La casa de la calle Charcas 2837 fue el hogar del destacado educador y hombre de letras Ricardo Rojas desde el a&ntilde;o 1929 hasta su muerte en 1957. Creador de la primera c&aacute;tedra de Literatura Argentina en la Universidad, poeta, ensayista y maestro con doctrina propia, sus escritos, que comprenden ensayos, trabajos eruditos, monograf&iacute;as, obras teatrales y poemas, se nutren en el di&aacute;logo de lo europeo con lo americano y en particular con la Am&eacute;rica ind&iacute;gena. Atendiendo a su concepci&oacute;n &ldquo;eur&iacute;ndica&rdquo;, Rojas inspir&oacute; la construcci&oacute;n de esta residencia, obra del arquitecto &Aacute;ngel Guido.</p>\r\n<p>Tras la muerte del escritor, la casa, con todo su patrimonio material e intelectual, fue transferida al Estado por su viuda, Julieta Quinteros de Rojas, dando as&iacute; cumplimiento al deseo del esposo de legarla para museo y biblioteca, a fin de que las generaciones futuras encontraran en ella un &aacute;mbito destinado al estudio y al debate de ideas. La Casa de Ricardo Rojas se integr&oacute; a los museos nacionales argentinos el 28 de abril de 1958 y fue declarada Monumento Hist&oacute;rico Nacional el 29 de mayo de ese mismo a&ntilde;o.</p>\r\n<p><strong>El Museo Casa de Ricardo Rojas &ndash; Instituto de Investigaciones</strong> brinda un edificio sobresaliente por su original arquitectura y un patrimonio diverso y valioso: muebles, obras de arte, piezas arqueol&oacute;gicas, objetos personales y reliquias. La Biblioteca, constituida por m&aacute;s de 20.000 vol&uacute;menes, es singular y rica en literaturas argentina, hispanoamericana y espa&ntilde;ola, puesto que Ricardo Rojas manten&iacute;a fluida comunicaci&oacute;n e intercambio con escritores, cr&iacute;ticos y docentes de diferentes pa&iacute;ses. El Archivo Documental se halla integrado por aproximadamente 100.000 documentos: la versi&oacute;n manuscrita de sus libros, pruebas de imprenta, obras in&eacute;ditas, fotograf&iacute;as y un amplio epistolario que, en su conjunto, reflejan y documentan la primera mitad del siglo XX.</p>\r\n<p><strong>Visi&oacute;n:</strong></p>\r\n<p>Ser una instituci&oacute;n especializada en literaturas argentina y latinoamericana. Establecer un &aacute;mbito propicio para reflexionar, debatir y divulgar los diferentes procesos socio-culturales desde el campo de la literatura e incentivar el encuentro entre pensadores, escritores, investigadores y lectores.</p>\r\n<p><strong>Misi&oacute;n:</strong></p>\r\n<p>La misi&oacute;n institucional del Museo es permitir investigar y difundir su patrimonio museol&oacute;gico y, a la par que promover el conocimiento de la obra de Ricardo Rojas, alentar el debate de ideas en torno a las culturas argentina y latinoamericana, el indoamericanismo y la literatura.</p>\r\n<p><strong>Horarios de visita:</strong><br />Martes a s&aacute;bados, de 11 a 19.</p>\r\n<p>Para acceder a las visitas guiadas escribir a:<br />visitas@casadericardorojas.gob.ar<br />comunidades@casadericardorojas.gob.ar</p>`},
      {placeTypeDescription: 'MUSEUM', description: `<p>En 1942, por ley 12.824 del 30 de septiembre, cuyo proyecto fue presentado en el Senado por el Dr. Alfredo L. Palacios, el escultor Rogelio Yrurtia y su esposa la pintora L&iacute;a Correa Morales, transfirieron al Estado su casa del barrio de Belgrano con su mobiliario y obras de arte. La misma se abri&oacute; al p&uacute;blico como museo en 1949. Yrurtia hab&iacute;a comprado una vieja casa de fines de siglo XIX con un terreno de 1.200 metros cuadrados. El mismo fue el responsable del dise&ntilde;o de la ampliaci&oacute;n y total reforma del edificio existente. El estilo elegido por el maestro fue el neocolonial, acorde con una revalorizaci&oacute;n de la tradici&oacute;n hisp&aacute;nica que por entonces se hab&iacute;a impuesto en c&iacute;rculos intelectuales. El arquitecto que realiz&oacute; los planos t&eacute;cnicos en base a los de Yrurtia y llev&oacute; a cabo la obra fue K. A. Schmit El constructor fue Pedro Rossi. La casa obtuvo en 1921 un premio municipal de arquitectura. Rogelio Yrurtia habla nacido en Buenos Aires el 6 de diciembre de 1879. En 1898 ingres&oacute; a la Escuela de la Sociedad Estimulo de Bellas Artes donde fue su maestro Lucio Correa Morales. A fines de 1899 obtuvo por concurso una de las becas instituidas por el Ministerio de Instrucci&oacute;n P&uacute;blica, para realizar estudios en Europa.</p>\r\n<p>Los monumentos realizados por Yrurtia se encuentran en la ciudad de Buenos Aires: Canto al Trabajo, en Paseo Col&oacute;n e Independencia, Monumento al Coronel Manuel Dorrego, en Suipacha y Viamonte, al Dr. Alejandro Castro en el Hospital de Cl&iacute;nicas, Justicia, en el Palacio de Tribunales, Mausoleo de Bernardino Rivadavia, en Plaza Miserere. Numerosos son los objetos coleccionados por los esposos Yrurtia a lo largo de sus viajes y durante las prolongadas estad&iacute;as en el exterior.Todos ellos eran expuestos en los distintos ambientes de su casa y, si les sumamos las obras del maestro, muchas de ellas de gran formato y las distintas pinturas y dibujos de su esposa y de sus amigos, obtendremos el conjunto que hoy puede parecer abigarrado pero que responde a un cierto gusto victoriano a&uacute;n en boga. La colecci&oacute;n de esculturas de Yrurtia es amplia. Pueden verse los modelos a tama&ntilde;o natural del Mois&eacute;s, perteneciente al Mausoleo de Bernardino Rivadavia, la Victoria del Monumento a Manuel Dorrego y la Justicia, entre otras.</p>\r\n<p>Numerosos retratos de bronce y en yeso, cabezas femeninas como Solicitude, Primavera, Daphne o Romana, estudios de torsos, pies y manos y otras obras completan el conjunto. Numerosas pinturas y dibujos de L&iacute;a Correa Morales, segunda esposa de Yrurtia e hija de su primer maestro, Lucio Correa Morales, pueblan los muros. Los g&eacute;neros m&aacute;s visitados por L&iacute;a fueron la naturaleza muerta y el retrato, especialmente femenino o infantil, adem&aacute;s de los croquis de bailarinas que tomaba del natural. La colecci&oacute;n de pinturas de artistas argentinos comprende obras de Mart&iacute;n Malharro, Angel Della Valle, Eduardo S&iacute;vori, Ces&aacute;reo Bernaldo de Quir&oacute;s, Octavio Pinto, Benito Quinquela Mart&iacute;n, Walter de Navazio, entre otros. Entre los extranjeros destaca una obra temprana de Pablo Picasso que Yrurtia adquiri&oacute; durante una de sus estancias en Par&iacute;s. Gran atracci&oacute;n por los textiles, alfombras y tapices, demuestra la vasta colecci&oacute;n que reunieron los esposos Yrurtia. Integran el conjunto batiks javaneses, tapices chinos bordados, alfombras anudadas de distinta procedencia, chales de Cachemira, textiles de M&eacute;xico y Bolivia, un tapiz de la Manufacture Nationale des Gobelins, etc.</p>\r\n<p>El mobiliario reunido es de estilo diverso. Hay varios armarios de estilo Renacimiento flamenco, mesas, sillas y sillones de procedencias espa&ntilde;ola, muebles ingleses y franceses de estilo imperio, provenzal, etc. Objetos de cer&aacute;mica se encuentran diseminados por las distintas vitrinas y sobre los muebles. Entre ellos destacan los de manufactura de Talavera de la Reina (Espa&ntilde;a) y Delft (Holanda). Son numerosos los objetos de uso dom&eacute;stico de peltre, bronce o cobre, como platos, pavas, calientacamas, velones, etc. Finalmente, y sin por ello agotar la variedad de piezas existentes en el museo, son abundantes los objetos de distinto tipo provenientes de China, Jap&oacute;n y Java.</p>\r\n<p><strong>Horario del museo:</strong></p>\r\n<div class=\"texto\">\r\n<div class=\"texto\">Mi&eacute;rcoles a domingo, de 10 a 18 h.</div>\r\n<div class=\"texto\">Visita guiada al p&uacute;blico: viernes a las 15 / S&aacute;bados, domingos y feriados a las 16:30.</div>\r\n<div class=\"texto\">&nbsp;</div>\r\n<div class=\"texto\">&nbsp;</div>\r\n<p>Grupos y estudiantes, solicitar turno a: <a href=\"mailto:educacion@museocasadeyrurtia.gob.ar\">educacion@museocasadeyrurtia.gob.ar</a><br />Biblioteca consultas on line a: <a href=\"mailto:biblioteca@museocasadeyrurtia.gob.ar\">biblioteca@museocasadeyrurtia.gob.ar</a><br />Consultas sobre las Colecciones y Archivo a: <a href=\"mailto:coleccionesyarchivo@museocasadeyrurtia.gob.ar\">coleccionesyarchivo@museocasadeyrurtia.gob.ar</a></p>\r\n</div>`},
      {placeTypeDescription: 'MUSEUM', description: `<p>El Complejo Hist&oacute;rico Cultural Manzana de las Luces depende del Ministerio de Cultura de la Naci&oacute;n.</p>\r\n<p>Fue creado en 1971 por el Decreto n&ordm; 4657/71 y ampliadas sus funciones por los decretos: 1185/73; 1454/74 y 1479/81. El decreto 108/2013 cambia su nombre de Comisi&oacute;n Nacional a Complejo Hist&oacute;rico Cultural Manzana de las Luces.</p>\r\n<p>Son sus objetivos la restauraci&oacute;n y conservaci&oacute;n de los edificios hist&oacute;ricos; la investigaci&oacute;n con relaci&oacute;n a instituciones, acontecimientos y personajes que desfilaron por la Manzana de las Luces; y la refuncionalizaci&oacute;n de los edificios a trav&eacute;s de la actividad cultural.</p>`},
      {placeTypeDescription: 'MUSEUM', description: `<p>El Complejo Hist&oacute;rico Cultural Manzana de las Luces depende del Ministerio de Cultura de la Naci&oacute;n.</p>\r\n<p>Fue creado en 1971 por el Decreto n&ordm; 4657/71 y ampliadas sus funciones por los decretos: 1185/73; 1454/74 y 1479/81. El decreto 108/2013 cambia su nombre de Comisi&oacute;n Nacional a Complejo Hist&oacute;rico Cultural Manzana de las Luces.</p>\r\n<p>Son sus objetivos la restauraci&oacute;n y conservaci&oacute;n de los edificios hist&oacute;ricos; la investigaci&oacute;n con relaci&oacute;n a instituciones, acontecimientos y personajes que desfilaron por la Manzana de las Luces; y la refuncionalizaci&oacute;n de los edificios a trav&eacute;s de la actividad cultural.</p>`},
      {placeTypeDescription: 'MUSEUM', description: `<p>El Complejo Hist&oacute;rico Cultural Manzana de las Luces depende del Ministerio de Cultura de la Naci&oacute;n.</p>\r\n<p>Fue creado en 1971 por el Decreto n&ordm; 4657/71 y ampliadas sus funciones por los decretos: 1185/73; 1454/74 y 1479/81. El decreto 108/2013 cambia su nombre de Comisi&oacute;n Nacional a Complejo Hist&oacute;rico Cultural Manzana de las Luces.</p>\r\n<p>Son sus objetivos la restauraci&oacute;n y conservaci&oacute;n de los edificios hist&oacute;ricos; la investigaci&oacute;n con relaci&oacute;n a instituciones, acontecimientos y personajes que desfilaron por la Manzana de las Luces; y la refuncionalizaci&oacute;n de los edificios a trav&eacute;s de la actividad cultural.</p>`},
    ]
    this.totalItems = this.places.length;
  }

  delete(place: any) {}

  update(id: number) {}

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    //this.loadPlaces();
  }
}
