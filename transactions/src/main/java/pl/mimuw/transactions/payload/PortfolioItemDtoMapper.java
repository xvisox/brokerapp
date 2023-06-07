package pl.mimuw.transactions.payload;

import pl.mimuw.transactions.models.Share;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PortfolioItemDtoMapper {
    public static List<PortfolioItemDto> mapShareListToPortfolioItemDtoList(List<Share> shares) {
        return shares.stream()
                .map(share -> PortfolioItemDto.builder()
                        .ticker(share.getTicker())
                        .amount(share.getAmount())
                        .build())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
