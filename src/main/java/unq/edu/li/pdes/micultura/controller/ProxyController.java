package unq.edu.li.pdes.micultura.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@Api(value = "MiCultura Controller")
@RequestMapping("/micultura")
@RequiredArgsConstructor
public class ProxyController {

	private final RestTemplate restTemplate;
	
    @GetMapping("/api/v2.0/museos")
    public Object getMuseos() {
        String url = "https://www.cultura.gob.ar/api/v2.0/museos/?limit=30";
        return restTemplate.getForObject(url, Object.class);
    }
    
    @GetMapping("/api/v2.0/institutos")
    public Object getInstitutos() {
        String url = "https://www.cultura.gob.ar/api/v2.0/institutos";
        return restTemplate.getForObject(url, Object.class);
    }
    @GetMapping("/api/v2.0/organismos")
    public Object getOrganismos() {
        String url = "https://www.cultura.gob.ar/api/v2.0/organismos/?limit=20&offset=120";
        return restTemplate.getForObject(url, Object.class);
    }
    @GetMapping("/api/v2.0/convocatorias")
    public Object getConvocatorias() {
        String url = "https://www.cultura.gob.ar/api/v2.0/convocatorias/?limit=20&offset=120";
        return restTemplate.getForObject(url, Object.class);
    }
    @GetMapping("/api/v2.0/tramites")
    public Object getTramites() {
        String url = "https://www.cultura.gob.ar/api/v2.0/tramites/";
        return restTemplate.getForObject(url, Object.class);
    }

}
