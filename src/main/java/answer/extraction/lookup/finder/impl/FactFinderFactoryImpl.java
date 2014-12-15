package answer.extraction.lookup.finder.impl;

import answer.extraction.lookup.finder.FactFinder;
import answer.extraction.lookup.finder.FactFinderFactory;
import answer.extraction.lookup.finder.impl.AgeFinder;
import answer.extraction.lookup.finder.impl.BirthPlaceFinder;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by prasad on 15/12/2014.
 */
public class FactFinderFactoryImpl implements FactFinderFactory {

    private Map<String, FactFinder> m_termMapper;

    public FactFinderFactoryImpl() {
        m_termMapper = Maps.newHashMap();
        m_termMapper.put("how old", new AgeFinder());
        m_termMapper.put("age", new AgeFinder());
        m_termMapper.put("place of birth", new BirthPlaceFinder());
        m_termMapper.put("birth place", new BirthPlaceFinder());
    }
    @Override
    public FactFinder getFactFinderFor(String term) {
        return m_termMapper.get(term);
    }
}
