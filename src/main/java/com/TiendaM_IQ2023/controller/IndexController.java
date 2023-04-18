package com.TiendaM_IQ2023.controller;


import com.TiendaM_IQ2023.dao.UsuarioDao;
import com.TiendaM_IQ2023.domain.Carrito;
import com.TiendaM_IQ2023.domain.CarritoDetalle;
import com.TiendaM_IQ2023.domain.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.TiendaM_IQ2023.service.ArticuloService;
import com.TiendaM_IQ2023.service.CarritoDetalleService;
import com.TiendaM_IQ2023.service.CarritoService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;



@Controller
public class IndexController {

    @Autowired
    ArticuloService articuloService;
    
    @Autowired
    UsuarioDao usuarioDao;
    
    @Autowired
    CarritoService carritoService;
    
    @Autowired
    CarritoDetalleService carritoDetalleService;

    @GetMapping("/")
    public String inicio(Model model, HttpServletRequest request) {
        
        //OBTENER EL USUARIO LOGGEADO
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails user = null;
        
        if (principal instanceof UserDetails) {
            user = (UserDetails)principal;
        }
        
        boolean esCliente = false;
        
        //VALIDAR SI ES UN USUARIO LOGGEADO
        if (user != null) {
            //CONSULTAR DETALLES DEL USUARIO
            Usuario usuario = usuarioDao.findByUsername(user.getUsername());
            
            //VALIDAR SI EL USUARIO ES DE UN CLIENTE
            if (usuario.getIdCliente() != null && usuario.getIdCliente() != 0) {
                esCliente = true;
                
                //CONSULTAMOS EL CARRITO DEL CLIENTE
                Carrito carrito = carritoService.getCarritoCliente(usuario.getIdCliente());
                
                //GUARDAMOS EN SESION LOS VALORES IMPORTANTES
                request.getSession().setAttribute("idCliente", usuario.getIdCliente());
                request.getSession().setAttribute("idCarrito", carrito.getIdCarrito());
                request.getSession().setAttribute("esCliente", esCliente);
                
                //CONSULTAR ITEMS DEL CARRITO
                List<CarritoDetalle> carritoDetalles = carritoDetalleService.getCarritoDetalles(carrito.getIdCarrito());
                model.addAttribute("cantidadArticulosCarrito", carritoDetalles.size());
            }
        }

        var articulos = articuloService.getArticulos(true);
        model.addAttribute("articulos", articulos);
        model.addAttribute("esCliente",esCliente);

        return "index";
    }

}
