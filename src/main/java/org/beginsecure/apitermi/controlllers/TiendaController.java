package org.beginsecure.apitermi.controlllers;

import jakarta.servlet.http.HttpServletResponse;
import org.beginsecure.apitermi.entities.Tienda;
import org.beginsecure.apitermi.services.TiendaPdfService;
import org.beginsecure.apitermi.services.TiendaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/tiendas")
public class TiendaController {
    private final TiendaService servicio;
    private final TiendaPdfService pdfService;

    public TiendaController(TiendaService servicio, TiendaPdfService pdfService) {
        this.servicio = servicio;
        this.pdfService = pdfService;
    }
    // 1. Mostrar la página principal con la lista de todas las tiendas
    @GetMapping
    public String listarTiendas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(required = false) String agencia,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Tienda> paginaDeTiendas;

        if (agencia != null && !agencia.trim().isEmpty()) {
            paginaDeTiendas = servicio.findByAgenciaPaginated(agencia.trim(), pageable);
            model.addAttribute("agenciaFiltro", agencia.trim());
        } else {
            paginaDeTiendas = servicio.findAllPaginated(pageable);
            model.addAttribute("agenciaFiltro", "");
        }

        model.addAttribute("tiendas", paginaDeTiendas.getContent());
        model.addAttribute("paginaActual", paginaDeTiendas);
        model.addAttribute("tamanoActual", size);

        // --- NUEVA LÍNEA: Pasamos la lista de agencias únicas al HTML ---
        model.addAttribute("listaAgencias", servicio.obtenerAgenciasUnicas());

        return "lista-tiendas";
    }
    // 2. Mostrar la página con el formulario para crear una tienda nueva
    @GetMapping("/nueva")
    public String mostrarFormularioDeCreacion(Model model) {
        // Le pasamos un objeto Tienda vacío al HTML para que el formulario lo rellene
        model.addAttribute("tienda", new Tienda());

        // Spring buscará un archivo llamado "formulario-tienda.html"
        return "formulario-tienda";
    }
    // 3. Recibir los datos del formulario HTML y guardarlos en la base de datos
    @PostMapping("/guardar")
    public String guardarTienda(@ModelAttribute Tienda tienda) {
        servicio.save(tienda);

        // Después de guardar, redirigimos al usuario a la página de la lista principal
        return "redirect:/tiendas";
    }
    // 4. Borrar una tienda (En HTML tradicional, los enlaces simples hacen peticiones GET)
    @GetMapping("/borrar/{id}")
    public String borrarTienda(@PathVariable String id) {
        servicio.deleteById(id);

        // Después de borrar, recargamos la lista
        return "redirect:/tiendas";
    }
    // 5. Mostrar formulario para editar una tienda existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEdicion(@PathVariable String id, Model model) {
        return servicio.findById(id).map(tienda -> {
            // Si la tienda existe, la pasamos al formulario (ya irá relleno)
            model.addAttribute("tienda", tienda);
            // ¡Reutilizamos el mismo HTML de creación!
            return "formulario-tienda";
        }).orElse("redirect:/tiendas"); // Si por algún error el ID no existe, volvemos a la lista
    }
    @GetMapping("/exportar-pdf")
    public void exportarAPdf(@RequestParam(required = false) String agencia, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=tiendas_" + (agencia != null ? agencia : "todas") + ".pdf";
        response.setHeader(cabecera, valor);

        // Obtenemos los datos filtrados (sin paginación para que salgan todos en el PDF)
        List<Tienda> tiendas;
        if (agencia != null && !agencia.isEmpty()) {
            tiendas = servicio.findByAgencia(agencia); // Este método ya lo tenías en el Service
        } else {
            tiendas = servicio.findAll();
        }

        pdfService.exportar(response, tiendas, agencia);
    }
}
