package br.unitins.irs.crud.resource;

import br.unitins.irs.crud.service.PdfService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Blocking
@Produces(MediaType.TEXT_HTML)
@Path("/pdf")
public class PdfResource {

    @Inject
    PdfService service;

    @Inject
    Template index;

    @GET
    public TemplateInstance get() {
        return index.instance();
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create() {
        return Response
                .seeOther(UriBuilder.fromUri("/person").build())
                .build();
    }
}
