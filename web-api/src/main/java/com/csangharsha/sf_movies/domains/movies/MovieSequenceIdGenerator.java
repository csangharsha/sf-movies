package com.csangharsha.sf_movies.domains.movies;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class MovieSequenceIdGenerator extends SequenceStyleGenerator {

    public static final String ID_SEPARATOR_PARAMETER = "idSeparator";
    public static final String ID_SEPARATOR_DEFAULT = "_";

    private String format;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Movie movie = ((Movie)object);
        return String.format(format,
                                StringUtils.replace(
                                        movie.getTitle(), " ", ID_SEPARATOR_DEFAULT
                                ),
                                StringUtils.replace(
                                        StringUtils.removeEnd(movie.getLocations(), "."), " ", ID_SEPARATOR_DEFAULT
                                )
        );
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(LongType.INSTANCE, params, serviceRegistry);
        String codeNumberSeparator = ConfigurationHelper.getString(ID_SEPARATOR_PARAMETER, params, ID_SEPARATOR_DEFAULT);
        this.format = "%1$s" + codeNumberSeparator + "%2$s";
    }
}
