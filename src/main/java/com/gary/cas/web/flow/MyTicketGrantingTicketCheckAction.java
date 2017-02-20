package com.gary.cas.web.flow;

import javax.validation.constraints.NotNull;

import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.util.StringUtils;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class MyTicketGrantingTicketCheckAction {

    /** TGT does not exist event ID={@value}. */
    public static final String NOT_EXISTS = "notExists";

    /** TGT invalid event ID={@value}. */
    public static final String INVALID = "invalid";

    /** TGT valid event ID={@value}. */
    public static final String VALID = "valid";

    /** Ticket registry searched for TGT by ID. */
    @NotNull
    private final TicketRegistry ticketRegistry;


    /**
     * Creates a new instance with the given ticket registry.
     *
     * @param registry Ticket registry to query for valid tickets.
     */
    public MyTicketGrantingTicketCheckAction(final TicketRegistry registry) {
        this.ticketRegistry = registry;
    }

    /**
     * Determines whether the TGT in the flow request context is valid.
     *
     * @param requestContext Flow request context.
     *
     * @return {@link #NOT_EXISTS}, {@link #INVALID}, or {@link #VALID}.
     */
    public Event checkValidity(final RequestContext requestContext) {

        final String tgtId = WebUtils.getTicketGrantingTicketId(requestContext);
        if (!StringUtils.hasText(tgtId)) {
            return new Event(this, NOT_EXISTS);
        }

        final Ticket ticket = this.ticketRegistry.getTicket(tgtId);
        return new Event(this, ticket != null && !ticket.isExpired() ? VALID : INVALID);
    }
}
