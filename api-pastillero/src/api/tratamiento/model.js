import mongoose, { Schema } from 'mongoose'

const tratamientoSchema = new Schema({

  nombreTratamiento:{
    type: String
  },

  periodo_toma: {
    type: Integer
  },
  
  diasDuracionTratamiento: {
    type: String
  }
}, {
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

tratamientoSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      periodo_toma: this.periodo_toma,
      diasDuracionTratamiento: this.diasDuracionTratamiento,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Tratamiento', tratamientoSchema)

export const schema = model.schema
export default model
