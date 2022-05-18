package com.fms.pms.domain.commands;

import com.fms.pms.events.RequestFullyApprovedBody;
import com.fms.pms.models.Errors;
import com.fms.pms.models.Header;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class HandleRequestFullyApprovedCommand extends BaseCommand {

  private RequestFullyApprovedBody body;

  @Builder
  public HandleRequestFullyApprovedCommand(
      final Header header, final RequestFullyApprovedBody body, final Errors errors) {
    super(header, errors);
    this.body = body;
  }
}
