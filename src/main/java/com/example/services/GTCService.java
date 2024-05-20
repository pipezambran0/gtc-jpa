/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.services;

import com.example.PersistenceManager;
import com.example.models.Usuario;
import com.example.models.UsuarioDTO;
import com.example.models.Camion;
import com.example.models.CamionDTO;
import com.example.models.Remision;
import com.example.models.RemisionDTO;
import com.example.models.Solicitud;
import com.example.models.SolicitudDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Ivan Zambrano and Sergio Mora
 */
@Path("/gtc")
@Produces(MediaType.APPLICATION_JSON)
public class GTCService {

    @PersistenceContext(unitName = "GTCPU")
    EntityManager entityManager;

    @PostConstruct
    public void init() {
        try {
            entityManager
                    = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Servicios relacionados a los Usuarios ------------------------------------
    @GET
    @Path("/verUsuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarios() {
        Query q = entityManager.createQuery("select u from Usuario u order by u.surname ASC");
        List<Usuario> usuarios = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(usuarios).build();
    }

    @GET
    @Path("/verClientes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientes() {
        Query q = entityManager.createQuery("select u from Usuario u WHERE u.role = 'Cliente' order by u.surname ASC");
        List<Usuario> usuarios = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(usuarios).build();
    }

    @GET
    @Path("/verPropietarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPropietarios() {
        Query q = entityManager.createQuery("select u from Usuario u WHERE u.role = 'Propietario' order by u.surname ASC");
        List<Usuario> usuarios = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(usuarios).build();
    }

    @GET
    @Path("/verConductores")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConductores() {
        Query q = entityManager.createQuery("select u from Usuario u WHERE u.role = 'Conductor' order by u.surname ASC");
        List<Usuario> usuarios = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(usuarios).build();
    }

    @POST
    @Path("/agregarUsuario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUsuario(UsuarioDTO usuarioDTO) {
        JSONObject rta = new JSONObject();
        Usuario usuarioTmp = new Usuario();
        usuarioTmp.setAddress(usuarioDTO.getAddress());
        usuarioTmp.setAge(usuarioDTO.getAge());
        usuarioTmp.setCellphone(usuarioDTO.getCellphone());
        usuarioTmp.setName(usuarioDTO.getName());
        usuarioTmp.setSurname(usuarioDTO.getSurname());
        usuarioTmp.setEmail(usuarioDTO.getEmail());
        usuarioTmp.setPassword(usuarioDTO.getPassword());
        usuarioTmp.setRole(usuarioDTO.getRole());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(usuarioTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(usuarioTmp);
            rta.put("usuario_id", usuarioTmp.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            usuarioTmp = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin",
                "*").entity(rta).build();
    }

    @DELETE
    @Path("/eliminarUsuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUsuario(@PathParam("id") Long id) throws JSONException {
        JSONObject response = new JSONObject();
        try {
            entityManager.getTransaction().begin();
            Usuario usuario = entityManager.find(Usuario.class, id);
            if (usuario != null) {
                entityManager.remove(usuario);
                entityManager.getTransaction().commit();
                response.put("message", "Usuario eliminado exitosamente");
                return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(response).build();
            } else {
                response.put("message", "Usuario no encontrado");
                return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity(response).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            response.put("message", "Error al eliminar usuario");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity(response).build();
        } finally {
            entityManager.clear();
            entityManager.close();
        }
    }

    @PUT
    @Path("/editarUsuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUsuario(@PathParam("id") Long id, UsuarioDTO usuarioDTO) throws JSONException {
        JSONObject response = new JSONObject();
        try {
            entityManager.getTransaction().begin();
            Usuario usuario = entityManager.find(Usuario.class, id);
            if (usuario != null) {
                usuario.setAddress(usuarioDTO.getAddress());
                usuario.setAge(usuarioDTO.getAge());
                usuario.setCellphone(usuarioDTO.getCellphone());
                usuario.setName(usuarioDTO.getName());
                usuario.setSurname(usuarioDTO.getSurname());
                usuario.setEmail(usuarioDTO.getEmail());
                usuario.setPassword(usuarioDTO.getPassword());
                usuario.setRole(usuarioDTO.getRole());
                entityManager.merge(usuario);
                entityManager.getTransaction().commit();
                response.put("message", "Usuario editado correctamente");
                return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(response).build();
            } else {
                response.put("message", "Usuario no encontrado");
                return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity(response).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            response.put("message", "Error al editar Usuario");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity(response).build();
        } finally {
            entityManager.clear();
            entityManager.close();
        }
    }

    //Servicios relacionados a los Camiones ------------------------------------
    @GET
    @Path("/verCamiones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCamiones() {
        Query q = entityManager.createQuery("select u from Camion u order by u.placa ASC");
        List<Camion> camiones = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(camiones).build();
    }

    @POST
    @Path("/agregarCamion")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCamion(CamionDTO camionDTO) {
        JSONObject rta = new JSONObject();
        Camion camionTmp = new Camion();
        camionTmp.setPlaca(camionDTO.getPlaca());
        camionTmp.setMarca(camionDTO.getMarca());
        camionTmp.setModelo(camionDTO.getModelo());
        camionTmp.setCapacidadCarga(camionDTO.getCapacidadCarga());
        camionTmp.setTipoCarroceria(camionDTO.getTipoCarroceria());
        camionTmp.setViajes(0);
        camionTmp.setPesoTotalTransportado(0);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(camionTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(camionTmp);
            rta.put("camion_id", camionTmp.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            camionTmp = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin",
                "*").entity(rta).build();
    }

    @DELETE
    @Path("/eliminarCamion/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCamion(@PathParam("id") Long id) throws JSONException {
        JSONObject response = new JSONObject();
        try {
            entityManager.getTransaction().begin();
            Camion camion = entityManager.find(Camion.class, id);
            if (camion != null) {
                entityManager.remove(camion);
                entityManager.getTransaction().commit();
                response.put("message", "Camion eliminado exitosamente");
                return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(response).build();
            } else {
                response.put("message", "Camion no encontrado");
                return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity(response).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            response.put("message", "Error al eliminar camion");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity(response).build();
        } finally {
            entityManager.clear();
            entityManager.close();
        }
    }

    @PUT
    @Path("/editarCamion/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCamion(@PathParam("id") Long id, CamionDTO camionDTO) throws JSONException {
        JSONObject response = new JSONObject();
        try {
            entityManager.getTransaction().begin();
            Camion camion = entityManager.find(Camion.class, id);
            if (camion != null) {
                camion.setPlaca(camionDTO.getPlaca());
                camion.setMarca(camionDTO.getMarca());
                camion.setModelo(camionDTO.getModelo());
                camion.setCapacidadCarga(camionDTO.getCapacidadCarga());
                camion.setTipoCarroceria(camionDTO.getTipoCarroceria());
                camion.setViajes(camionDTO.getViajes());
                camion.setPesoTotalTransportado(camionDTO.getPesoTotalTransportado());
                entityManager.merge(camion);
                entityManager.getTransaction().commit();
                response.put("message", "Camion editado correctamente");
                return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(response).build();
            } else {
                response.put("message", "Camion no encontrado");
                return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity(response).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            response.put("message", "Error al editar Camion");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity(response).build();
        } finally {
            entityManager.clear();
            entityManager.close();
        }
    }

    //Servicios relacionados a las Solicitudes ---------------------------------
    @GET
    @Path("/verSolicitudes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSolicitudes() {
        Query q = entityManager.createQuery("select u from Solicitud u order by u.propietarioCarga ASC");
        List<Solicitud> solicitudes = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(solicitudes).build();
    }

    @POST
    @Path("/agregarSolicitud")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSolicitud(SolicitudDTO solicitudDTO) {
        JSONObject rta = new JSONObject();
        Solicitud solicitudTmp = new Solicitud();
        solicitudTmp.setFecha(solicitudDTO.getFecha());
        solicitudTmp.setPropietarioCarga(solicitudDTO.getPropietarioCarga());
        solicitudTmp.setOrigen(solicitudDTO.getOrigen());
        solicitudTmp.setDestino(solicitudDTO.getDestino());
        solicitudTmp.setDimensiones(solicitudDTO.getDimensiones());
        solicitudTmp.setPeso(solicitudDTO.getPeso());
        solicitudTmp.setValorAsegurado(solicitudDTO.getValorAsegurado());
        solicitudTmp.setEmpaque(solicitudDTO.getEmpaque());
        solicitudTmp.setEstado(solicitudDTO.getEstado());
        solicitudTmp.setPropietarioCamion("***");
        solicitudTmp.setConductorCamion("+++");
        solicitudTmp.setReferenciaRemision("---");

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(solicitudTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(solicitudTmp);
            rta.put("solicitud_id", solicitudTmp.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            solicitudTmp = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin",
                "*").entity(rta).build();
    }

    @DELETE
    @Path("/eliminarSolicitud/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSolicitud(@PathParam("id") Long id) throws JSONException {
        JSONObject response = new JSONObject();
        try {
            entityManager.getTransaction().begin();
            Solicitud solicitud = entityManager.find(Solicitud.class, id);
            if (solicitud != null) {
                entityManager.remove(solicitud);
                entityManager.getTransaction().commit();
                response.put("message", "Solicitud eliminada exitosamente");
                return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(response).build();
            } else {
                response.put("message", "Solicitud no encontrada");
                return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity(response).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            response.put("message", "Error al eliminar Solicitud");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity(response).build();
        } finally {
            entityManager.clear();
            entityManager.close();
        }
    }

    @PUT
    @Path("/editarSolicitud/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSolicitud(@PathParam("id") Long id, SolicitudDTO solicitudDTO) throws JSONException {
        JSONObject response = new JSONObject();
        try {
            entityManager.getTransaction().begin();
            Solicitud solicitud = entityManager.find(Solicitud.class, id);
            if (solicitud != null) {
                solicitud.setFecha(solicitudDTO.getFecha());
                solicitud.setPropietarioCarga(solicitudDTO.getPropietarioCarga());
                solicitud.setOrigen(solicitudDTO.getOrigen());
                solicitud.setDestino(solicitudDTO.getDestino());
                solicitud.setDimensiones(solicitudDTO.getDimensiones());
                solicitud.setPeso(solicitudDTO.getPeso());
                solicitud.setValorAsegurado(solicitudDTO.getValorAsegurado());
                solicitud.setEmpaque(solicitudDTO.getEmpaque());
                solicitud.setEstado(solicitudDTO.getEstado());
                solicitud.setPropietarioCamion(solicitudDTO.getPropietarioCamion());
                solicitud.setReferenciaRemision(solicitudDTO.getReferenciaRemision());

                entityManager.merge(solicitud);
                entityManager.getTransaction().commit();
                response.put("message", "Solicitud editada correctamente");
                return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(response).build();
            } else {
                response.put("message", "Solicitud no encontrada");
                return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity(response).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            response.put("message", "Error al editar Solicitud");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity(response).build();
        } finally {
            entityManager.clear();
            entityManager.close();
        }
    }

    //Servicios relacionados a la remision -------------------------------------
    @GET
    @Path("/verRemisiones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRemisiones() {
        Query q = entityManager.createQuery("select u from Remision u order by u.placa ASC");
        List<Remision> remisiones = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(remisiones).build();
    }

    @POST
    @Path("/agregarRemision")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRemision(RemisionDTO remisionDTO) {
        JSONObject rta = new JSONObject();
        Remision remisionTmp = new Remision();
        remisionTmp.setFechaRecogida(remisionDTO.getFechaRecogida());
        remisionTmp.setHoraRecogida(remisionDTO.getHoraRecogida());
        remisionTmp.setOrigen(remisionDTO.getOrigen());
        remisionTmp.setDestino(remisionDTO.getDestino());
        remisionTmp.setPlaca(remisionDTO.getPlaca());
        remisionTmp.setConductor(remisionDTO.getConductor());
        remisionTmp.setRuta(remisionDTO.getRuta());
        remisionTmp.setValoracion(0);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(remisionTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(remisionTmp);
            rta.put("remision_id", remisionTmp.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            remisionTmp = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin",
                "*").entity(rta).build();
    }

    @DELETE
    @Path("/eliminarRemision/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRemision(@PathParam("id") Long id) throws JSONException {
        JSONObject response = new JSONObject();
        try {
            entityManager.getTransaction().begin();
            Remision remision = entityManager.find(Remision.class, id);
            if (remision != null) {
                entityManager.remove(remision);
                entityManager.getTransaction().commit();
                response.put("message", "Remision eliminada exitosamente");
                return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(response).build();
            } else {
                response.put("message", "Remision no encontrada");
                return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity(response).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            response.put("message", "Error al eliminar Remision");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity(response).build();
        } finally {
            entityManager.clear();
            entityManager.close();
        }
    }

    @PUT
    @Path("/editarRemision/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRemision(@PathParam("id") Long id, RemisionDTO remisionDTO) throws JSONException {
        JSONObject response = new JSONObject();
        try {
            entityManager.getTransaction().begin();
            Remision remision = entityManager.find(Remision.class, id);
            if (remision != null) {
                remision.setFechaRecogida(remisionDTO.getFechaRecogida());
                remision.setHoraRecogida(remisionDTO.getHoraRecogida());
                remision.setOrigen(remisionDTO.getOrigen());
                remision.setDestino(remisionDTO.getDestino());
                remision.setPlaca(remisionDTO.getPlaca());
                remision.setConductor(remisionDTO.getConductor());
                remision.setRuta(remisionDTO.getRuta());
                remision.setValoracion(remisionDTO.getValoracion());
                entityManager.merge(remision);
                entityManager.getTransaction().commit();
                response.put("message", "Remision editada correctamente");
                return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(response).build();
            } else {
                response.put("message", "Remision no encontrada");
                return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity(response).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            response.put("message", "Error al editar Remision");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity(response).build();
        } finally {
            entityManager.clear();
            entityManager.close();
        }
    }

    // BackEnd 2 ---------------------------------------------------------------
    // Cliente - Radicación de la Solicitud ------------------------------------
    @POST
    @Path("/radicarSolicitud/{clienteId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response radicarSolicitud(SolicitudDTO solicitudDTO, @PathParam("clienteId") Long clienteId) {
        JSONObject response = new JSONObject();
        Solicitud solicitud = new Solicitud();
        try {
            entityManager.getTransaction().begin();
            Usuario cliente = entityManager.find(Usuario.class, clienteId);
            solicitud.setFecha(solicitudDTO.getFecha());
            solicitud.setPropietarioCarga(cliente.getName() + " " + cliente.getSurname());
            solicitud.setOrigen(solicitudDTO.getOrigen());
            solicitud.setDestino(solicitudDTO.getDestino());
            solicitud.setDimensiones(solicitudDTO.getDimensiones());
            solicitud.setPeso(solicitudDTO.getPeso());
            solicitud.setValorAsegurado(solicitudDTO.getValorAsegurado());
            solicitud.setEmpaque(solicitudDTO.getEmpaque());
            solicitud.setEstado("Disponible");
            solicitud.setPropietarioCamion("***");
            solicitud.setConductorCamion("+++");
            solicitud.setReferenciaRemision("---");

            entityManager.persist(solicitud);
            entityManager.getTransaction().commit();
            entityManager.refresh(solicitud);

            response.put("Esta es la referencia de la solicitud: ", solicitud.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            solicitud = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(response).build();
    }

    // Propietario - Consulta Solicitudes Disponibles --------------------------
    @GET
    @Path("/verSolicitudesDisponibles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verSolicitudesDisponibles() {
        Query q = entityManager.createQuery("SELECT s FROM Solicitud s WHERE s.estado = 'Disponible'");
        List<Solicitud> solicitudes = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(solicitudes).build();
    }

    // Propietario - Acepta Solicitud Disponible -------------------------------
    @POST
    @Path("/aceptarSolicitud/{solicitudId}/{propietarioId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response aceptarSolicitud(@PathParam("solicitudId") Long solicitudId, @PathParam("propietarioId") Long propietarioId) throws JSONException {
        JSONObject response = new JSONObject();
        try {
            entityManager.getTransaction().begin();
            Solicitud solicitud = entityManager.find(Solicitud.class, solicitudId);
            Usuario propietario = entityManager.find(Usuario.class, propietarioId);

            if (solicitud != null && propietario != null) {
                solicitud.setPropietarioCamion(propietario.getName() + " " + propietario.getSurname());
                solicitud.setEstado("Aceptada");
                entityManager.merge(solicitud);
                entityManager.getTransaction().commit();
                entityManager.refresh(solicitud);

                response.put("Esta es la referencia de la solicitud aceptada: ", solicitud.getId());
            } else {
                response.put("message", "Solicitud o Propietario no encontrado");
                return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity(response).build();
            }
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            response.put("message", "Error al aceptar la solicitud");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity(response).build();
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(response).build();
    }

    // Propietario - Asigna Conductor ------------------------------------------
    @POST
    @Path("/asignarConductor/{solicitudId}/{conductorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response asignarConductor(@PathParam("solicitudId") Long solicitudId, @PathParam("conductorId") Long conductorId) throws JSONException {
        JSONObject response = new JSONObject();
        try {
            entityManager.getTransaction().begin();
            Solicitud solicitud = entityManager.find(Solicitud.class, solicitudId);
            Usuario conductor = entityManager.find(Usuario.class, conductorId);

            if (solicitud != null && conductor != null) {
                solicitud.setConductorCamion(conductor.getName() + " " + conductor.getSurname());
                entityManager.merge(solicitud);
                entityManager.getTransaction().commit();
                entityManager.refresh(solicitud);

                response.put("Esta es la referencia de la solicitud: ", solicitud.getId());
            } else {
                response.put("message", "Solicitud o Conductor no encontrado");
                return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity(response).build();
            }
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            response.put("message", "Error al asignar el conductor a la solicitud");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity(response).build();
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(response).build();
    }

    // Conductor - Genera la Remision ------------------------------------------
    @POST
    @Path("/generarRemision/{solicitudId}/{conductorId}/{camionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generarRemision(@PathParam("solicitudId") Long solicitudId, @PathParam("conductorId") Long conductorId, @PathParam("camionId") Long camionId, RemisionDTO remisionDTO) throws JSONException {
        JSONObject rta = new JSONObject();
        try {
            entityManager.getTransaction().begin();

            Usuario conductor = entityManager.find(Usuario.class, conductorId);
            Solicitud solicitud = entityManager.find(Solicitud.class, solicitudId);
            Camion camion = entityManager.find(Camion.class, camionId);

            if (conductor == null) {
                rta.put("message", "Conductor no encontrado");
                return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity(rta).build();
            }

            if (solicitud == null) {
                rta.put("message", "Solicitud no encontrada");
                return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity(rta).build();
            }

            Remision remisionTmp = new Remision();
            remisionTmp.setFechaRecogida(remisionDTO.getFechaRecogida());
            remisionTmp.setHoraRecogida(remisionDTO.getHoraRecogida());
            remisionTmp.setOrigen(remisionDTO.getOrigen());
            remisionTmp.setDestino(remisionDTO.getDestino());
            remisionTmp.setPlaca(camion.getPlaca());
            remisionTmp.setConductor(conductor.getName() + " " + conductor.getSurname());
            remisionTmp.setRuta(remisionDTO.getRuta());

            entityManager.persist(remisionTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(remisionTmp);

            entityManager.getTransaction().begin();
            solicitud.setReferenciaRemision(remisionTmp.getId().toString());
            entityManager.merge(solicitud);
            entityManager.getTransaction().commit();
            entityManager.refresh(solicitud);

            rta.put("Esta es la referencia de la remision:", remisionTmp.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            rta.put("message", "Error al generar la remisión");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity(rta).build();
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta).build();
    }

    // Conductor - Cerrar Remision ---------------------------------------------
    @PUT
    @Path("/cerrarRemision/{remisionId}/{valoracion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cerrarRemision(@PathParam("remisionId") Long remisionId, @PathParam("valoracion") int valoracion) throws JSONException {
        JSONObject response = new JSONObject();
        try {
            entityManager.getTransaction().begin();
            Remision remision = entityManager.find(Remision.class, remisionId);

            if (remision != null) {
                remision.setValoracion(valoracion);

                entityManager.merge(remision);
                entityManager.getTransaction().commit();

                response.put("message", "Valoracion Realizada");
                return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(response).build();
            } else {
                response.put("message", "Remisión no encontrada");
                return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity(response).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            response.put("message", "Error al cerrar la remisión y registrar la valoracion");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity(response).build();
        } finally {
            entityManager.clear();
            entityManager.close();
        }
    }

    // Conductor - Finalizar Solicitud -----------------------------------------
    @PUT
    @Path("/finalizarSolicitud/{solicitudId}/{camionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response finalizarSolicitud(@PathParam("solicitudId") Long solicitudId, @PathParam("camionId") Long camionId) throws JSONException {
        JSONObject response = new JSONObject();
        try {
            entityManager.getTransaction().begin();
            Solicitud solicitud = entityManager.find(Solicitud.class, solicitudId);
            Camion camion = entityManager.find(Camion.class, camionId);

            if (solicitud != null) {
                solicitud.setEstado("Finalizada");
                camion.setPesoTotalTransportado(camion.getPesoTotalTransportado() + solicitud.getPeso());
                camion.setViajes(camion.getViajes() + 1);
                entityManager.merge(solicitud);
                entityManager.merge(camion);
                entityManager.getTransaction().commit();
                response.put("message", "Solicitud finalizada correctamente");
                return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(response).build();
            } else {
                response.put("message", "Solicitud no encontrada");
                return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity(response).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            response.put("message", "Error al finalizar la solicitud");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity(response).build();
        } finally {
            entityManager.clear();
            entityManager.close();
        }
    }

    // Propietario - Estadisticas del Camion -----------------------------------
    @GET
    @Path("/estadisticasCamion/{camionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEstadisticasCamion(@PathParam("camionId") Long camionId) throws JSONException {
        JSONObject response = new JSONObject();
        try {
            entityManager.getTransaction().begin();
            Camion camion = entityManager.find(Camion.class, camionId);
            entityManager.getTransaction().commit();

            if (camion != null) {
                response.put("placa", camion.getPlaca());
                response.put("viajes", camion.getViajes());
                response.put("pesoTotalTransportado", camion.getPesoTotalTransportado());

                return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(response).build();
            } else {
                response.put("message", "Camión no encontrado");
                return Response.status(Response.Status.NOT_FOUND).header("Access-Control-Allow-Origin", "*").entity(response).build();
            }
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            response.put("message", "Error al obtener las estadísticas del camión");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*").entity(response).build();
        } finally {
            entityManager.clear();
            entityManager.close();
        }
    }

}
