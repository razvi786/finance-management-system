package com.fms.pms.domain.commands;

import com.fms.pms.events.UpdatePaymentBody;
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
public class HandleUpdatePaymentCommand extends BaseCommand {

  private UpdatePaymentBody body;

  @Builder
  public HandleUpdatePaymentCommand(
      final Header header, final UpdatePaymentBody body, final Errors errors) {
    super(header, errors);
    this.body = body;
  }
}
