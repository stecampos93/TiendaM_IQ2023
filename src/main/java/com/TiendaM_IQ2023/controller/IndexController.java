package com.TiendaM_IQ2023.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.TiendaM_IQ2023.dao.ClienteDao;
import org.springframework.web.bind.annotation.GetMapping;
import com.TiendaM_IQ2023.domain.Cliente;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Stiphen Campos
 */
@Slf4j
@Controller
public class IndexController {

    @Autowired
    ClienteDao clienteDao;

    @GetMapping("/")
    public String inicio(Model model) {
//        log.info("Ahora desde MVC");
//        model.addAttribute("Mensaje", "Hola desde el controllador");
//        
//        Cliente cliente = new Cliente("Jonathan", "Brenes Blanco", "jbrenes@gmail.com", "88447799");
//        model.addAttribute("objetoCliente", cliente);
//        
//        Cliente cliente2 = new Cliente("Juan", "Brenes Blanco", "jbrenes@gmail.com", "88447799");
//        Cliente cliente3 = new Cliente("Juan", "Brenes Blanco", "jbrenes@gmail.com", "88447799");
//        
//        List<Cliente> clientes = Arrays.asList(cliente, cliente2, cliente3);

        var clientes = clienteDao.findAll();
        model.addAttribute("clientes", clientes);

        return "index";
    }

}
